import { Subject } from 'rxjs';
import { ComplainUserModel } from './../models/ComplainUser.model';
import { Router } from '@angular/router';
import { AlertService } from './alert.service';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root'})
export class UserService {

  constructor(private http: ApplicationHttpClientService,
              private alertService: AlertService,
              private router: Router) {}

private  user: ComplainUserModel;
userSubject = new Subject<ComplainUserModel>();

}
