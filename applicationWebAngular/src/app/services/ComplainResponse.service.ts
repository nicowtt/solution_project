import { MatSnackBar } from '@angular/material/snack-bar';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Injectable } from '@angular/core';
import { ApplicationHttpClientService } from './applicationHttpClient.service';

@Injectable({ providedIn: 'root'})
export class ComplainResponseService {

  constructor(private http: ApplicationHttpClientService,
              private snackBar: MatSnackBar) {}


  changeResponsePopularity(response: ComplainResponseModel, userPseudo: string, onError: Function) {
    this.http
    .post<ComplainResponseModel>('/changeResponsePopularity/' + userPseudo, response)
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
