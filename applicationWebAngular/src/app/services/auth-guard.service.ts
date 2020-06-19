import { MatSnackBar } from '@angular/material/snack-bar';
import { ApplicationHttpClientService } from './applicationHttpClient.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router,
              private http: ApplicationHttpClientService,
              private alertService: AlertService,
              private snackBar: MatSnackBar) { }

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    return new Promise(
      (resolve, reject) => {
        // find email userInProgress
        // let userInProgress = JSON.parse(localStorage.getItem('currentUser'));
        this.http
          .get<any>('/userStateChanged')
          .subscribe(
            (data) => {
              const userStillOk = data;
              console.log('User still authorized ?: ' + userStillOk);
              if (data) {
                resolve(true);
              } else {
                this.router.navigate(['/auth', 'signin']);
                resolve(false);
              }
            },
            (error) => {
              if (error.status === 401) {
                this.router.navigate(['/auth', 'signin']);
              }
              this.snackBar.open('Vous devez être connecté pour avoir accés à cette fonction', '', {
                duration: 3000,
                verticalPosition: 'top'
              });
            });
      }
    );
  }
}
