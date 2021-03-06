import { MatCheckboxModule, MatFormField, MatFormFieldModule, MatInputModule, MatSelectModule, MatSnackBarModule, MatTooltipModule } from '@angular/material';
import { ApplicationHttpClientService } from './services/applicationHttpClient.service';
import { AuthService } from './services/auth.service';
import { AuthGuardService } from './services/auth-guard.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { HeaderComponent } from './header/header.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { AlertComponent } from './alert/alert.component';
import {HeaderInterceptorService} from './services/header-interceptor.service';
import { MainDisplayComponent } from './main-display/main-display.component';
import { ResponseDisplayComponent } from './response-display/response-display.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CreateNewRequestComponent } from './create-new-request/create-new-request.component';
import { RxStompService } from '@stomp/ng2-stompjs';
import { RequestWebsocketService } from './services/request.websocket.service';
import { ResponseWebSocketService } from './services/response.websocket.service';

const appRoutes: Routes = [
  { path: 'newRequest', canActivate: [AuthGuardService], component: CreateNewRequestComponent},
  { path: 'response/:id', canActivate: [AuthGuardService], component: ResponseDisplayComponent},
  { path: 'auth/signin', component: SigninComponent },
  { path: 'auth/signup', component: SignupComponent },
  { path: 'main', component: MainDisplayComponent},
  { path: '', redirectTo: 'main', pathMatch: 'full'},
  { path: '**', redirectTo: 'main'}
];

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    HeaderComponent,
    AlertComponent,
    MainDisplayComponent,
    ResponseDisplayComponent,
    CreateNewRequestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    NoopAnimationsModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptorService, multi: true },
    AuthService,
    AuthGuardService,
    ApplicationHttpClientService,
    RxStompService,
    RequestWebsocketService,
    ResponseWebSocketService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
