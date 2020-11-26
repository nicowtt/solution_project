import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ComplainUserModel } from '../models/ComplainUser.model';
import { map } from 'rxjs/operators';
import { ApplicationHttpClientService } from './applicationHttpClient.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<ComplainUserModel>;
  public currentUser: Observable<ComplainUserModel>;

  private userInProgress: ComplainUserModel;


  constructor(private http: ApplicationHttpClientService,
              private snackBar: MatSnackBar,
              private router: Router) {
    this.currentUserSubject = new BehaviorSubject<ComplainUserModel>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): ComplainUserModel {
    return this.currentUserSubject.value;
  }


  /*
  signInUser(email, password) {
    return this.http.post<any>('/checkLogin', { email, password })
      .pipe(map(user => {
        if (user && user.token) {
          this.userInProgress = user;
          // console.log('only token: ' + this.userInProgress.token);
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
          localStorage.setItem('currentUserToken', JSON.stringify(this.userInProgress.token));
          this.currentUserSubject.next(user);
          console.log('local quand sigIn: ' + localStorage.getItem('currentUser'));
        }
        return user;
      }));
  }
  */

  signInUser(email, password, onSucces: any, onError: any) {
    return this.http
      .post<any>('/checkLogin', { email, password })
      .subscribe(
        (Response) => {
          this.userInProgress = Response;
          // console.log('only token: ' + this.userInProgress.token);
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(this.userInProgress));
          localStorage.setItem('currentUserToken', JSON.stringify(this.userInProgress.token));
          this.currentUserSubject.next(this.userInProgress);
          console.log('local quand sigIn: ' + localStorage.getItem('currentUser'));
          onSucces();
        },
        (error) => {
            onError();
        }
      );
  }

  userStateChange(onSuccess: any) {
    return this.http
    .get<any>('/userStateChanged/')
    .subscribe(
      (Response) => {
        onSuccess();
      },
      (error) => {
        console.log ('Token périmé!');
        this.snackBar.open('Vous devez vous re-connecter !', '', {
          duration: 5000,
          verticalPosition: 'top'
        });
        this.router.navigate(['auth/signin']);
      });
    }


  /**
   * User logOut
   */
  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    // console.log('local quand logOut: ' + localStorage.getItem('currentUser'));
  }

  // /**
  //  * for create new user
  //  * @param email
  //  * @param password
  //  */
  // createNewUser(newUser: Object) {
  //   console.log(newUser);
  //   return this.http.post<any>('/newUser', {newUser})
  //     .pipe(map(newUser => {
  //       console.log(newUser);
  //         return newUser;
  //     }
  //     ));
  // }
}


