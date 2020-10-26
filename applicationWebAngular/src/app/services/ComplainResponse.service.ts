import { AlertService } from './alert.service';
import { onErrorResumeNext, Subject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Injectable } from '@angular/core';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { ComplainCommentModel } from '../models/ComplainComment.model';
import { ComplainRequestModel } from '../models/ComplainRequest.model';

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

  addResponse(objectResponse: ComplainResponseModel, requestId: string, onSuccess: Function) {
    return this.http
      .post<ComplainResponseModel>('/newResponse/' + requestId, objectResponse)
      .subscribe(
        (response) => {
          onSuccess();
        },
        (error) => {
          this.snackBar.open('Veuillez recommencer', '', {
            duration: 3000,
            verticalPosition: 'top'
          });
        }
      );
  }

  updateResponse(responseToUpdate: ComplainResponseModel) {
    return this.http
    .post<ComplainResponseModel>('/updateResponse', responseToUpdate)
    .subscribe(
      (response) => {
        this.snackBar.open('La réponse à été mise à jour', '', {
          duration: 3000,
          verticalPosition: 'top'
        });
      },
      (error) => {
        this.snackBar.open('erreur ,veuillez recommencer!', '', {
          duration: 3000,
          verticalPosition: 'top'
        });
      }
    );
  }

  deleteResponse(responseToDelete: ComplainResponseModel, onSuccess: any) {
    return this.http
    .post<ComplainResponseModel>('/deleteResponse', responseToDelete)
    .subscribe(
      (response) => {
        this.snackBar.open('reponse suprimmée', '', {
          duration: 3000,
          verticalPosition: 'top'
        });
        onSuccess();
      },
      (error) => {
        this.snackBar.open('erreur, veuillez recommencez', '', {
          duration: 3000,
          verticalPosition: 'top'
        });
      }
    );
  }


  addComment(newComment: ComplainCommentModel, responseId: string, onSuccess: any) {
    return this.http
    .post<ComplainRequestModel>('/newComment/' + responseId, newComment)
    .subscribe(
      (response) => {
        onSuccess();
      },
      (error) => {
        this.snackBar.open('Ooups!, Veuillez recommencer.', '', {
          duration: 3000,
          verticalPosition: 'top'
        });
      }
    );
  }



}
