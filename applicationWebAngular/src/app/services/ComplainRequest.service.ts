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

    requestSubject = new Subject<ComplainRequestModel[]>();

    emitRequests() {
      this.requestSubject.next(this.requestsList);
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

    increaseRequestPopularity(request: ComplainRequestModel, userPseudo: string, onError: Function) {
      this.http
      .post<ComplainRequestModel>('/increaseRequestPopularity/' + userPseudo, request)
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

}
