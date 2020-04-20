import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {UserModel} from '../models/ComplainUser.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  currentUser: UserModel;

  constructor(private authService: AuthService,
              private router: Router) {
    // this.authService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
  }

  /**
   *  when user clic on SignOut
   */
  onSignOut() {
    // this.authService.logout();
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUserToken');
    this.router.navigate(['/sales']);
  }

  onPersonalSpace() {
    this.router.navigate(['/personalSpace']);
  }
}
