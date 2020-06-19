import { Router, ActivatedRoute } from '@angular/router';
import { ComplainThemeService } from './../services/ComplainTheme.service';
import { ComplainResponseModel } from './../models/ComplainResponse.model';
import { Subscription } from 'rxjs';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ComplainRequestService } from '../services/ComplainRequest.service';

@Component({
  selector: 'app-response-display',
  templateUrl: './response-display.component.html',
  styleUrls: ['./response-display.component.css']
})
export class ResponseDisplayComponent implements OnInit, OnDestroy {


  requestsList: ComplainRequestModel[];
  requestsSubscription: Subscription;
  requestConcerned: ComplainRequestModel;

  responsesList: ComplainResponseModel[];

  constructor(private complainRequestService: ComplainRequestService,
              private complainThemeService: ComplainThemeService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.requestConcerned = new ComplainRequestModel();
    // subscription
    this.requestsSubscription = this.complainRequestService.requestSubject.subscribe(
      (requests: ComplainRequestModel[]) => {
        this.requestsList = requests;
        this.requestConcerned = this.requestsList[id];
        this.responsesList = this.requestsList[id].complainResponses;
      }
    );
    this.complainRequestService.getAllRequests(() => {
      this.complainRequestService.emitRequests();
    });
  }

  ngOnDestroy() {
    this.requestsSubscription.unsubscribe();
  }

}
