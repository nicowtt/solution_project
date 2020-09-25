import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject } from 'rxjs';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { AlertService } from './alert.service';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { Injectable } from '@angular/core';
import { ComplainUserModel } from './../models/ComplainUser.model';


@Injectable({ providedIn: 'root'})
export class ComplainRequestService {

  constructor(private http: ApplicationHttpClientService,
              private alertService: AlertService,
              private snackBar: MatSnackBar) {}

    private requestsList: ComplainRequestModel[] = [];
    private request: ComplainRequestModel;

    requestsSubject = new Subject<ComplainRequestModel[]>();
    requestSubject = new Subject<ComplainRequestModel>();

    emitRequests() {
      this.requestsSubject.next(this.requestsList);
    }
    emitRequest() {
      this.requestSubject.next(this.request);
    }

    getAllRequests(onSuccess: Function) {
      this.http
      .get<ComplainRequestModel[]>('/getAllRequests')
      .subscribe(
        (response) => {
          this.requestsList = response;
          this.emitRequests();
          onSuccess();
        },
        (error) => {
          console.log('erreur de chargement des thèmes!' + error);
          this.alertService.error('erreur reseau, veuillez recommencez!');
        }
      );
    }

    getOneRequest(requestId: number, onSuccess: Function) {
      this.http
      .get<ComplainRequestModel>('/getOneRequest/' + requestId )
      .subscribe(
        (response) => {
          this.request = response;
          this.emitRequest();
          onSuccess();
        },
        (error) => {
          console.log('erreur lors du chargement de la request!' + error);
          this.alertService.error('erreur reseau, veuillez recommencez!');
        }
      );
    }

    changeRequestPopularity(request: ComplainRequestModel, userPseudo: string, onError: Function) {
      this.http
      .post<ComplainRequestModel>('/changeRequestPopularity/' + userPseudo, request)
      .subscribe(
        (response) => {
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

    addRequest(newRequest: ComplainRequestModel, onSucces: Function) {
      return this.http
      .post<ComplainRequestModel>('/newRequest', newRequest)
      .subscribe(
        (response) => {
          onSucces();
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
