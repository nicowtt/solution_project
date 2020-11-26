import { AlertService } from './../../services/alert.service';
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  signInForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute,
              private alertService: AlertService) { }

  ngOnInit() {
    this.initForm();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/Sales';

  }

  initForm() {
    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/[0-9a-zA-Z]{3,}/)]]
    });
  }

  // easy access to form fields
  get f() { return this.signInForm.controls; }

  /**
   * when submit signIn form
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.signInForm.invalid) {
      return;
    }

    this.loading = true;
    this.authService.signInUser(this.f.email.value, this.f.password.value, () => {
      // succes
      this.router.navigate([this.returnUrl]);
    },
    () => {
      // error
      this.alertService.error("Le nom d'utilisateur ou le mot de passe est incorrect");
        setTimeout(() => {
          this.alertService.clear();
        }, 2000);
        console.log("Le nom d'utilisateur ou le mot de passe est incorrect");
      this.loading = false;
    });
  }

  newSignUp() {
    this.router.navigate(['auth/signup']);
  }

}
