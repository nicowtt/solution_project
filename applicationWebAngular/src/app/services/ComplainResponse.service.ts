import { AlertService } from './alert.service';
import { Subject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Injectable } from '@angular/core';
import { ApplicationHttpClientService } from './applicationHttpClient.service';

@Injectable({ providedIn: 'root' })
export class ComplainResponseService {

  constructor(private http: ApplicationHttpClientService,
              private snackBar: MatSnackBar,
              private alertService: AlertService) { }

  private requestResponses: ComplainResponseModel[] = [];

  requestResponsesSubject = new Subject<ComplainResponseModel[]>();

  emitRequests() {
    this.requestResponsesSubject.next(this.requestResponses);
  }

  getAllResponseForOneRequest(requestId: string, onSuccess: Function) {
    this.http
      .get<ComplainResponseModel[]>('/getAllResponsesForOneRequest/' + requestId)
      .subscribe(
        (response) => {
          this.requestResponses = response;
          this.emitRequests();
          onSuccess();
        },
        (error) => {
          console.log('erreur de chargement des responses!' + error);
          this.alertService.error('erreur reseau, veuillez recommencez!');
        }
      );
  }



  changeResponsePopularity(response: ComplainResponseModel, userPseudo: string, onError: Function, onSuccess: Function) {
    this.http
      .post<ComplainResponseModel>('/changeResponsePopularity/' + userPseudo, response)
      .subscribe(
        (response) => {
          onSuccess();
        },
        (error) => {
          this.snackBar.open("Vous avez déja voté!", '', {
            duration: 3000,
            verticalPosition: 'top'
          });
          onError();
        }
      );
  }



}
