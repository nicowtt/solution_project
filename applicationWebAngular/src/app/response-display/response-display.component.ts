import { MatSnackBar } from '@angular/material/snack-bar';
import { FormGroup, Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { ComplainResponseService } from './../services/ComplainResponse.service';
import { ComplainUserModel } from './../models/ComplainUser.model';
import { AuthService } from './../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Subscription } from 'rxjs';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ComplainRequestService } from '../services/ComplainRequest.service';
import { ComplainCommentModel } from '../models/ComplainComment.model';

@Component({
  selector: 'app-response-display',
  templateUrl: './response-display.component.html',
  styleUrls: ['./response-display.component.css']
})
export class ResponseDisplayComponent implements OnInit, OnDestroy {

  currentUser: ComplainUserModel;
  userConnected: boolean;
  userConnectedIsModerator: boolean;

  idOfRequest: number;

  requestSubscription: Subscription;
  requestConcerned: ComplainRequestModel;

  requestResponsesSubscription: Subscription;
  responseConcerned: ComplainResponseModel;

  requestResponses: ComplainResponseModel[] = [];

  commentForm: FormGroup;
  submitted = false;
  moderateForm: FormGroup;

  preFillresponse: string;
  preFillResponseIndex: number;
  preFillComment: string;
  preFillLink: string;
  moderateResponse: ComplainResponseModel;
  moderateComment: string;
  moderateCommentIndex: number;
  moderateLink: string;

  isNotCollapsed = -1;
  oldIsNotCollapsed = -1;
  seeComments = false;

  link = false;

  constructor(private complainRequestService: ComplainRequestService,
              private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar,
              private authService: AuthService,
              private complainResponseService: ComplainResponseService,
              private formBuilder: FormBuilder) {
                this.authService.currentUser.subscribe(x => this.currentUser = x);
              }

  ngOnInit() {
    this.initForm();
    this.idOfRequest = this.route.snapshot.params.id;
    this.requestConcerned = new ComplainRequestModel();
    // subscription to request concerned
    this.requestSubscription = this.complainRequestService.requestSubject.subscribe(
      (request: ComplainRequestModel) => {
        this.requestConcerned = request;
        this.calculateRequestDiffFromTodayTo(request);
      }
    );
    this.complainRequestService.getOneRequest(this.idOfRequest, () => {
      this.complainRequestService.emitRequest();

      // if request is here -> subscription to responses concerned
      this.updateResponses();
      });
    // moderateur value
    if (this.currentUser.role === 'ADMIN') {
      this.userConnectedIsModerator = true;
    } else {
      this.userConnectedIsModerator = false;
    }
  }

  initForm() {
    this.commentForm = this.formBuilder.group({
      comment: ['', [Validators.required]],
      addLink: ['', [Validators.required]]
    });
    this.moderateForm = this.formBuilder.group({
      responseModerate: [this.preFillresponse],
      commentModerate: [this.preFillComment],
      linkModerate: [this.preFillLink]
    });
  }

  // easy access to form fields
  get cf() { return this.commentForm.controls; }

  addLink(link: boolean) {
    if (link) {
      this.link = false;
    } else {
      this.link = true;
    }
  }

  updateResponses() {
    this.requestResponsesSubscription = this.complainResponseService.requestResponsesSubject.subscribe(
      (responses: ComplainResponseModel[]) => {
        this.requestResponses = responses;
      }
    );
    this.complainResponseService.getAllResponseForOneRequest(this.requestConcerned.id, () => {
      this.requestResponses.forEach( response => {
        this.calculateResponseDiffFromTodayTo(response);
        this.calculateTotalComment();
      });
      this.requestResponses.sort(this.comparePopularity); // for display bigger is upper
    });
  }

  calculateTotalComment() {
    this.requestResponses.forEach(response => {
      response.totalComment = response.commentList.length;
    });
  }

  ngOnDestroy() {
    this.requestSubscription.unsubscribe();
    this.requestResponsesSubscription.unsubscribe();
  }

  preFill(response: ComplainResponseModel) {
    this.preFillresponse = response.response;
    this.moderateResponse = response;
    this.preFillLink = response.extLink;
    this.initForm();
  }

  preFillForComment(response: ComplainResponseModel, indexOfResponse: number) {
    this.preFillresponse = response.response;
    this.preFillResponseIndex = indexOfResponse;
    this.isNotCollapsed = this.preFillResponseIndex;
    this.initForm();
  }

  preFillForModerateComment(response: ComplainResponseModel, indexOfComment: number) {
    this.preFillresponse = response.response;
    this.preFillComment = response.commentList[indexOfComment].comment;
    this.moderateCommentIndex = indexOfComment;
    this.moderateResponse = response;
    this.initForm();
  }

  onSubmitResponse(requestId: string) {
    this.submitted = true;

    // stop here if form is invalid
    if (this.commentForm.get('comment').value === '' && !this.link) {
      return;
    }
    if (this.commentForm.get('comment').value === '' ||
        this.commentForm.get('addLink').value === '' &&
        this.link) {
      return;
    }

    const response = new ComplainResponseModel();
    response.complainUserId = this.currentUser.id;
    response.creatorPseudo = this.currentUser.pseudo;
    response.creatorEmail = this.currentUser.email;
    response.response = this.commentForm.get('comment').value;
    response.extLink = this.commentForm.get('addLink').value;
    response.creationDate = new Date().toLocaleString();
    response.requestId = requestId;
    response.commentList = [];
    console.log(response.toString());
    this.complainResponseService.addResponse(response, requestId, () => {
      this.updateResponses();
      this.commentForm.get('comment').setValue('');
    });
  }

  calculateResponseDiffFromTodayTo(response) {
    const currentDate = new Date();
    const dateSent = new Date(response.creationDate);

    response.creationDaysUntilToday = Math.floor((Date.UTC(currentDate.getFullYear(), currentDate.getMonth(),
    currentDate.getDate()) - Date.UTC(dateSent.getFullYear(), dateSent.getMonth(),
    dateSent.getDate())) / (1000 * 60 * 60 * 24));

    response.creationHoursUntilToday = currentDate.getHours() - dateSent.getHours();

    response.creationMinutesUntilToday = currentDate.getMinutes() - dateSent.getMinutes();

  }

  calculateRequestDiffFromTodayTo(request) {
    const currentDate = new Date();
    const dateSent = new Date(request.creationDate);

    request.creationDaysUntilToday = Math.floor((Date.UTC(currentDate.getFullYear(), currentDate.getMonth(),
    currentDate.getDate()) - Date.UTC(dateSent.getFullYear(), dateSent.getMonth(),
    dateSent.getDate())) / (1000 * 60 * 60 * 24));

    request.creationHoursUntilToday = currentDate.getHours() - dateSent.getHours();

    request.creationMinutesUntilToday = currentDate.getMinutes() - dateSent.getMinutes();

  }

  changePopularity(index: number, change: string) {
    let userAlreadyVoted = false;

    // check if user has already voted
    this.requestResponses[index].userWhoChangePopularityList.forEach(userWhoChange => {
      if (userWhoChange === this.currentUser.pseudo) {
        userAlreadyVoted = true;
      }
    });

    if (!userAlreadyVoted) {
      if (change === '+') {
        this.requestResponses[index].popularity++;
      } else if (change === '-') {
        this.requestResponses[index].popularity--;
      }

      const userPseudo = this.currentUser.pseudo;
      this.complainResponseService.changeResponsePopularity(this.requestResponses[index], userPseudo, () => {
        // on succes
        this.updateResponses();
      });
    } else {
  this.snackBar.open("Vous avez déja voté!", '', {
    duration: 3000,
    verticalPosition: 'top'
  });
}
  }

  comparePopularity(a: ComplainResponseModel, b: ComplainResponseModel) {
    const responseA = a.popularity;
    const responseB = b.popularity;

    let comparison = 0;
    if (responseA < responseB) {
      comparison = 1;
    } else if (responseA > responseB) {
      comparison = -1;
    }
    return comparison;
  }

  onSubmitModerateResponse() {
    this.moderateResponse.response = this.moderateForm.get('responseModerate').value;
    this.moderateResponse.extLink = this.moderateForm.get('linkModerate').value;
    console.log(this.moderateResponse);
    this.complainResponseService.updateResponse(this.moderateResponse);
  }

  onSubmitModerateComment() {
    this.moderateComment = this.moderateForm.get('commentModerate').value;
    console.log(this.moderateComment);
    this.moderateResponse.commentList[this.moderateCommentIndex].comment = this.moderateComment;
    console.log(this.moderateResponse);
    this.complainResponseService.updateResponse(this.moderateResponse);
  }


  onSubmitComment() {
    if (this.requestResponses[this.preFillResponseIndex].commentList == null) {
      this.requestResponses[this.preFillResponseIndex].commentList = [];
    }
    const newComment = new ComplainCommentModel();
    newComment.comment = this.commentForm.get('comment').value;
    newComment.creatorEmail = this.currentUser.email;
    newComment.creatorPseudo = this.currentUser.pseudo;
    newComment.responseId = this.requestResponses[this.preFillResponseIndex].id;
    this.complainResponseService.addComment(newComment, this.requestResponses[this.preFillResponseIndex].id, () => {
      this.updateResponses();
      this.commentForm.get('comment').setValue('');
    });
  }

  deleteResponse(index: number) {
    if (confirm('Supprimer cette réponse?')) {
      this.complainResponseService.deleteResponse(this.requestResponses[index], () => {
        this.requestResponses.splice(index, 1);
        console.log(this.requestResponses);
      });
    }
  }

  deleteComment(response: ComplainResponseModel ,index: number) {
    if (confirm('Supprimmer ce commentaire?')) {
        response.commentList.splice(index, 1);
        this.complainResponseService.updateResponse(response);
    }
  }

  showComments(indexOfresponse: number) {
    if (this.seeComments) {
      if (indexOfresponse === this.oldIsNotCollapsed ) {
        this.seeComments = false;
      } else if (indexOfresponse !== this.oldIsNotCollapsed ) {
        this.oldIsNotCollapsed = this.isNotCollapsed;
        this.seeComments = true;
      } else {
        this.seeComments = true;
      }
    } else {
      this.seeComments = true;
      this.oldIsNotCollapsed = this.isNotCollapsed;
    }
  }
}
