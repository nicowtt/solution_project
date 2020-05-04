import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserModel} from "../../models/ComplainUser.model";
import {ComplainUserService} from "../../services/complainUser.service";
import {first} from "rxjs/operators";
import {HttpErrorResponse} from "@angular/common/http";
import { getLocaleDateTimeFormat } from '@angular/common';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private userService: ComplainUserService,
              private route: ActivatedRoute,
              private router: Router
              ) {

    // redirect to home if already logged in
    if (this.authService.currentUserValue) {
      this.router.navigate(['/#']);
    }
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.signUpForm = this.formBuilder.group({
      firstName:['', [Validators.required]],
      name:['', [Validators.required]],
      pseudo:['',[Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/[0-9a-zA-Z]{3,}/)]]
    });
  }

  // easy access to form fields
  get f() { return this.signUpForm.controls; }

  onSubmit() {

    this.submitted = true;

    // stop here if form is invalid
    if (this.signUpForm.invalid) {
      console.log('formulaire error');
      return;
    }
    // mapp from form
    this.loading = true;
    const newUser = new UserModel();
    newUser.name = this.f.name.value;
    newUser.firstName = this.f.firstName.value;
    newUser.pseudo = this.f.pseudo.value;
    newUser.email = this.f.email.value;
    newUser.password = this.f.password.value;
    newUser.creationDate = new Date;

    this.userService.AddUser(newUser, () => {
      this.router.navigate(['auth/signin']);
    }, () => {
      this.loading = false;
    })
  }

}
