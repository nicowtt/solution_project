import { Subject } from 'rxjs';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { AlertService } from './alert.service';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { Injectable } from '@angular/core';


@Injectable({ providedIn: 'root'})
export class ComplainRequestService {

  constructor(private http: ApplicationHttpClientService,
              private alertService: AlertService) {}

    private requestsList: ComplainRequestModel[] = [];

    requestSubject = new Subject<ComplainRequestModel[]>();

    emitRequests() {
      this.requestSubject.next(this.requestsList);
    }

    getAllRequests(onSuccess: Function) {
      this.http
      .get<ComplainRequestModel[]>('/GetAllRequests')
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

}
