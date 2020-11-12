import { FormBuilder, FormGroup } from '@angular/forms';
import { BottleModel } from './../models/Bottles.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AlertService } from './../services/alert.service';
import { ComplainUserModel } from './../models/ComplainUser.model';
import { AuthService } from './../services/auth.service';
import { Router } from '@angular/router';
import { ComplainRequestService } from './../services/ComplainRequest.service';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-main-display',
  templateUrl: './main-display.component.html',
  styleUrls: ['./main-display.component.css']
})
export class MainDisplayComponent implements OnInit, OnDestroy {

  userConnectedIsModerator: boolean;

  requestsList: ComplainRequestModel[];
  requestsSubscription: Subscription;

  currentUser: ComplainUserModel;
  userConnected: boolean;

  bottles: BottleModel[] = new Array();

  preFillRequest: string;
  moderateRequest: ComplainRequestModel;
  prefillThemeRequest: string;

  moderateForm: FormGroup;

  dayForForget = 7;

  constructor(private complainRequestService: ComplainRequestService,
              private router: Router,
              private authService: AuthService,
              private alertService: AlertService,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder
  ) {
    this.authService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.initForm();
    this.updateRequests();
  }

  initForm() {
    this.moderateForm = this.formBuilder.group({
      requestModerate: [this.preFillRequest],
      requestThemeModerate: [this.prefillThemeRequest]
    });
  }

  updateRequests() {
    // subscription
    this.requestsSubscription = this.complainRequestService.requestsSubject.subscribe(
      (requests: ComplainRequestModel[]) => {
        this.requestsList = requests;
      }
    );
    //init
    this.complainRequestService.getAllRequestsNotForgotten(() => {
      this.requestsList.forEach(request => {
        this.countNbrOfResponse(request);
        this.calculateRequestDiffFromTodayTo(request);
      });
      this.fillBottles();
      this.requestsList.sort(this.comparePopularity); // bigger is upper
    });
    // user connected
    if (this.authService.currentUserValue) {
      this.userConnected = true;
      if (this.currentUser.role === 'ADMIN') {
        this.userConnectedIsModerator = true;
      } else {
        this.userConnectedIsModerator = false;
      }
    } else {
      this.userConnected = false;
    }
  }

  ngOnDestroy() {
    this.requestsSubscription.unsubscribe();
  }

  preFill(request: ComplainRequestModel) {
    this.preFillRequest = request.request;
    this.moderateRequest = request;
    this.prefillThemeRequest = request.themeName;
    this.initForm();
  }

  onSubmitModerate() {
    this.moderateRequest.request = this.moderateForm.get('requestModerate').value;
    this.moderateRequest.themeName = this.moderateForm.get('requestThemeModerate').value;
    console.log(this.moderateRequest);
    this.complainRequestService.updateRequest(this.moderateRequest);
  }

  comparePopularity(a, b) {
    const requestA = a.popularity;
    const requestB = b.popularity;

    let comparison = 0;
    if (requestA < requestB) {
      comparison = 1;
    } else if (requestA > requestB) {
      comparison = -1;
    }
    return comparison;
  }

  showResponses(requestId: string) {
    this.router.navigate(['/response', requestId]);
  }

  countNbrOfResponse(request: ComplainRequestModel) {
    const nbrOfResponse = request.complainResponsesId.length;
    request.nbrResponse = nbrOfResponse;
  }

  changePopularity(index: number, change: string) {
    let userAlreadyVoted = false;

    if (this.userConnected) {
      // check if user has already voted
      this.requestsList[index].userWhoChangePopularityList.forEach(userWhoChange => {
        if (userWhoChange === this.currentUser.pseudo) {
          userAlreadyVoted = true;
        }
      });

      if (!userAlreadyVoted) {
        if (change === '+') {
          this.requestsList[index].popularity++;
        } else if (change === '-') {
          this.requestsList[index].popularity--;
        }

        const userPseudo = this.currentUser.pseudo;
        this.complainRequestService.changeRequestPopularity(this.requestsList[index], userPseudo, () => {
          this.updateRequests();
        });
      } else {
        this.snackBar.open("Vous avez déja voté!", '', {
          duration: 3000,
          verticalPosition: 'top'
        });
      }

    } else {
      this.snackBar.open('Vous devez être connecté pour avoir accés à cette fonction', '', {
        duration: 3000,
        verticalPosition: 'top'
      }
      );
    }
  }

  calculateRequestDiffFromTodayTo(request) {
    const currentDate = new Date();
    const dateSent = new Date(request.creationDate);

    request.creationDaysUntilToday = Math.floor((Date.UTC(currentDate.getFullYear(), currentDate.getMonth(),
    currentDate.getDate()) - Date.UTC(dateSent.getFullYear(), dateSent.getMonth(),
    dateSent.getDate())) / (1000 * 60 * 60 * 24));

    request.creationHoursUntilToday = currentDate.getHours() - dateSent.getHours();

    request.creationMinutesUntilToday = currentDate.getMinutes() - dateSent.getMinutes();
  }

  calculateDiffFromTodayTo(inputDate) {
    const currentDate = new Date();
    const dateSent = new Date(inputDate);

    return Math.floor((Date.UTC(currentDate.getFullYear(), currentDate.getMonth(),
    currentDate.getDate()) - Date.UTC(dateSent.getFullYear(), dateSent.getMonth(),
    dateSent.getDate())) / (1000 * 60 * 60 * 24));
  }

  calculateBottleWidth(nbrOfResponse: number) {
    let width = 5;
    if (nbrOfResponse > 0) {
      width += (nbrOfResponse / 2);
    }
    if (width > 15) {
      width = 15;
    }
    return width;
  }

  fillBottles() {
    // INFO :poxXbottle -> min 0px, max 750px(picture) -> last response on request :today = 0px, tomorrow = 100px
    let posXbottle = 0;
    let posYbottle = 260;
    let bottleWidth = 5;
    let decal = true;
    // create bottles
    this.requestsList.forEach(request => {
      posXbottle = this.calculateDiffFromTodayTo(request.lastResponseDate);
      bottleWidth = this.calculateBottleWidth(request.nbrResponse);
      if (posXbottle !== 0) {
        posXbottle = posXbottle * 100;
      }
      // display decals
      if (decal) {
        posXbottle += 5;
        decal = false;
      } else {
        posXbottle -= 5;
        decal = true;
      }
      const bottle = new BottleModel(posYbottle + 'px', posXbottle + 'px', bottleWidth + '%');
      bottle.requestName = request.request;
      bottle.requestId = request.id;
      // check on console
      console.log(bottle);
      // for now we see bottle 7 days after request is posted
      if (posXbottle <= 705 && posYbottle <= 450) {
        this.bottles.push(bottle);
      }
      // increment
      //posYbottle += 15;
      posYbottle += 10;
    });
  }

  newRequest() {
    let nbrOfRequest = 0;
    this.requestsList.forEach(request => {
      nbrOfRequest++;
    });
    console.log(nbrOfRequest);
    if (nbrOfRequest < 20) {
      this.router.navigate(['/newRequest']);
    } else {
      this.snackBar.open('limite de sujets atteinte !, veuillez attendre qu\'un sujet soit oublié.', '', {
        duration: 5000,
        verticalPosition: 'top'
      });
    }

  }

  deleteRequest(index: number) {
    if(confirm('Supprimer cette requête?')) {
      this.complainRequestService.deleteRequest(this.requestsList[index], () => {
        this.requestsList.splice(index, 1);
      });
    }
  }
}
