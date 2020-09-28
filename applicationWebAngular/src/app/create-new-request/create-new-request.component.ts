import { ComplainRequestService } from './../services/ComplainRequest.service';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { Subscription } from 'rxjs';
import { ComplainThemeModel } from './../models/ComplainTheme.model';
import { ComplainThemeService } from './../services/ComplainTheme.service';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './../services/auth.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ComplainUserModel } from './../models/ComplainUser.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-new-request',
  templateUrl: './create-new-request.component.html',
  styleUrls: ['./create-new-request.component.css']
})
export class CreateNewRequestComponent implements OnInit {

  currentUser: ComplainUserModel;
  newRequestForm: FormGroup;
  submitted = false;
  loading = false;

  themesList: ComplainThemeModel[];
  themesSubscription: Subscription;

  select = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private complainThemeService: ComplainThemeService,
              private complainRequestService: ComplainRequestService
               ) {
                this.authService.currentUser.subscribe(x => this.currentUser = x);
               }

  ngOnInit(): void {
    this.initForm();
     // subscription
    this.themesSubscription = this.complainThemeService.themeSubject.subscribe(
      (themes: ComplainThemeModel[]) => {
        this.themesList = themes;
      }
    );
    this.complainThemeService.getAllThemes(() => {
    });
  }

  initForm() {
    this.newRequestForm = this.formBuilder.group({
      request: ['', [Validators.required]],
      theme: ['', [Validators.required]]
    });
  }

  // easy access to form fields
  get f() { return this.newRequestForm.controls; }

  onSubmit() {

    this.submitted = true;

    // stop here if form is invalid
    if (this.newRequestForm.invalid) {
      return;
    }
    this.loading = true;

    // map and send to back -> re-rout to main
    const newRequest = new ComplainRequestModel();
    newRequest.request = this.newRequestForm.get('request').value;
    newRequest.themeName = this.newRequestForm.get('theme').value;
    newRequest.creatorEmail = this.currentUser.email;
    newRequest.creatorPseudo = this.currentUser.pseudo;
    this.complainRequestService.addRequest(newRequest, () => {
      this.router.navigate(['main']);
    });
  }

  disableSelect(select: boolean) {
    if (select) {
      this.select = false;
    } else {
      this.select = true;
    }
  }

}
