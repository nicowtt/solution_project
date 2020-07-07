import { ComplainResponseService } from './../services/ComplainResponse.service';
import { ComplainUserModel } from './../models/ComplainUser.model';
import { AuthService } from './../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ComplainThemeService } from './../services/ComplainTheme.service';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Subscription } from 'rxjs';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ComplainRequestService } from '../services/ComplainRequest.service';

@Component({
  selector: 'app-response-display',
  templateUrl: './response-display.component.html',
  styleUrls: ['./response-display.component.css']
})
export class ResponseDisplayComponent implements OnInit, OnDestroy {

  currentUser: ComplainUserModel;
  userConnected: boolean;

  requestsList: ComplainRequestModel[];
  requestsSubscription: Subscription;

  requestSubscription: Subscription;
  requestConcerned: ComplainRequestModel;
  responseConcerned: ComplainResponseModel;

  responsesList: ComplainResponseModel[];

  constructor(private complainRequestService: ComplainRequestService,
              private route: ActivatedRoute,
              private authService: AuthService,
              private complainResponseService: ComplainResponseService) {
                this.authService.currentUser.subscribe(x => this.currentUser = x);
              }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.requestConcerned = new ComplainRequestModel();
    // subscription
    this.requestSubscription = this.complainRequestService.requestSubject.subscribe(
      (request: ComplainRequestModel) => {
        this.requestConcerned = request;
        request.dayUntilToday = this.calculateDiffFromTodayTo(request.creationDate);
      }
    );
    this.complainRequestService.getOneRequest(id, () => {
      this.complainRequestService.emitRequest();
      this.responsesList = this.requestConcerned.complainResponses;
      this.responsesList.forEach( response => {
        response.dayUntilToday = this.calculateDiffFromTodayTo(response.creationDate);
      });
    });
  }

  ngOnDestroy() {
    this.requestsSubscription.unsubscribe();
    this.requestSubscription.unsubscribe();
  }

  calculateDiffFromTodayTo(dateSent) {
    const currentDate = new Date();
    dateSent = new Date(dateSent);

    return Math.floor((Date.UTC(currentDate.getFullYear(), currentDate.getMonth(),
    currentDate.getDate()) - Date.UTC(dateSent.getFullYear(), dateSent.getMonth(),
    dateSent.getDate())) / (1000 * 60 * 60 * 24));
  }

  changePopularity(index: number, change: string) {
      if (change === '+') {
        this.requestConcerned.complainResponses[index].popularity++;
      } else if (change === '-') {
        this.requestConcerned.complainResponses[index].popularity--;
      }

      const userPseudo = this.currentUser.pseudo;

      this.complainResponseService.changeResponsePopularity(this.requestConcerned.complainResponses[index], userPseudo, () => {
        // on error
        if (change === '+') {
          this.requestConcerned.complainResponses[index].popularity--;
        } else if (change === '-') {
          this.requestConcerned.complainResponses[index].popularity++;
        }
      });


      this.responsesList.sort(this.comparePopularity);
  }

  comparePopularity(a, b) {
    const responseA = a.popularity;
    const responseB = b.popularity;

    let comparison = 0;
    if (responseA < responseB) {
      comparison = 1;
    } else if (responseA > responseB) {
      comparison = -1;
    }
    return comparison;
  }

}
