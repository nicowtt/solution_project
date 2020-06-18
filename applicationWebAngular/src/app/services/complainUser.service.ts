import { AlertService } from './alert.service';
import { Router } from '@angular/router';
import {ComplainUserModel} from '../models/ComplainUser.model';
import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {ApplicationHttpClientService} from './applicationHttpClient.service';

@Injectable({ providedIn: 'root'})
export class ComplainUserService {
  constructor(private http: ApplicationHttpClientService,
              private alertService : AlertService,
              private router: Router) { }

  private usersList: ComplainUserModel[] = [];
  usersSubject = new Subject<ComplainUserModel[]>();

  private user: ComplainUserModel;
  userSubject = new Subject<ComplainUserModel>();


  listEmailsSubject = new Subject<string[]>();
  userEmails: string[];

  emitUsers() {
    this.usersSubject.next(this.usersList.slice());
  }

  emitUserEmail() {
    this.listEmailsSubject.next(this.userEmails.slice());
  }

  emitUser() {
    this.userSubject.next(this.user);
  }

  /**
   * add new user
   * @param user
   * @constructor
   */
  AddUser(user: ComplainUserModel, onSucces: Function, onError:Function) {
    return this.http
    .post(`/newUser`, user)
    .subscribe(
      res => {
        this.alertService.success('Utilisateur enregistré', true);
        setTimeout(() => {
          this.alertService.clear();
        }, 3000);
        onSucces();
      },
      (error: any) => {
        if (error.error === "email already exist") {
          this.alertService.error("Erreur, l'email existe déjà", true);
          setTimeout(() => {
            this.alertService.clear();
          }, 3000);
        } else {
          this.alertService.error(error.message);
          setTimeout(() => {
            this.alertService.clear();
          }, 3000);
        }
        onError();
      }
    );
  }

  alertNetworkOff(error: any) {
    this.alertService.error('erreur reseau veuillez recommencer plus tard.', true);
    this.timeOutOffAlert5000();
  }

  timeOutOffAlert5000() {
    setTimeout(() => {
      this.alertService.clear();
    }, 5000);
  }
}
