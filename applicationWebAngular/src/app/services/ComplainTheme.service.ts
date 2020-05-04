import { AlertService } from './alert.service';
import { Subject } from 'rxjs';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { Injectable } from '@angular/core';
import { ComplainThemeModel } from '../models/ComplainTheme.model';


@Injectable({ providedIn: 'root'})
export class ComplainThemeService {

  constructor(private http: ApplicationHttpClientService,
              private alertService: AlertService) {}

  private themesList: ComplainThemeModel[] = [];

  themeSubject = new Subject<ComplainThemeModel[]>();

  emitThemes() {
    this.themeSubject.next(this.themesList);
  }

  getAllThemes(onSuccess: Function) {
    this.http
    .get<ComplainThemeModel[]>('/GetAllThemes')
    .subscribe(
      (response) => {
        this.themesList = response;
        this.emitThemes();
        onSuccess();
      },
      (error) => {
        console.log('erreur de chargement des thèmes!' + error);
        this.alertService.error('erreur reseau, veuillez recommencez!');
      }
    );
  }


}
