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

  requestSubscription: Subscription;
  requestConcerned: ComplainRequestModel;

  requestResponsesSubscription: Subscription;
  responseConcerned: ComplainResponseModel;

  requestResponses: ComplainResponseModel[] = [];

  constructor(private complainRequestService: ComplainRequestService,
              private route: ActivatedRoute,
              private authService: AuthService,
              private complainResponseService: ComplainResponseService) {
                this.authService.currentUser.subscribe(x => this.currentUser = x);
              }

  ngOnInit() {
    const id = this.route.snapshot.params.id;
    this.requestConcerned = new ComplainRequestModel();
    // subscription to request concerned
    this.requestSubscription = this.complainRequestService.requestSubject.subscribe(
      (request: ComplainRequestModel) => {
        this.requestConcerned = request;
        request.dayUntilToday = this.calculateDiffFromTodayTo(request.creationDate);
      }
    );
    this.complainRequestService.getOneRequest(id, () => {
      this.complainRequestService.emitRequest();

      // if request is here -> subscription to responses concerned
      this.requestResponsesSubscription = this.complainResponseService.requestResponsesSubject.subscribe(
        (responses: ComplainResponseModel[]) => {
          this.requestResponses = responses;
        }
      );
      this.complainResponseService.getAllResponseForOneRequest(this.requestConcerned.id, () => {
        this.requestResponses.forEach( response => {
          response.dayUntilToday = this.calculateDiffFromTodayTo(response.creationDate);
        });
        this.requestResponses.sort(this.comparePopularity); // for display bigger is upper
      });
      });
  }

  ngOnDestroy() {
    this.requestSubscription.unsubscribe();
    this.requestResponsesSubscription.unsubscribe();
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
        this.requestResponses[index].popularity++;
      } else if (change === '-') {
        this.requestResponses[index].popularity--;
      }

      const userPseudo = this.currentUser.pseudo;

      this.complainResponseService.changeResponsePopularity(this.requestResponses[index], userPseudo, () => {
        // on error
        if (change === '+') {
          this.requestResponses[index].popularity--;
        } else if (change === '-') {
          this.requestResponses[index].popularity++;
        }
      }, () => {
        // on succes
        this.requestResponses.sort(this.comparePopularity);
      });
  }

  comparePopularity(a: ComplainResponseModel, b: ComplainResponseModel) {
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
