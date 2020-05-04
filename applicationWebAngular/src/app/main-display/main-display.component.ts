import { ComplainRequestService } from './../services/ComplainRequest.service';
import { ComplainRequestModel } from './../models/ComplainRequest.model';
import { ComplainThemeService } from './../services/ComplainTheme.service';
import { Subscription } from 'rxjs';
import { ComplainThemeModel } from './../models/ComplainTheme.model';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-main-display',
  templateUrl: './main-display.component.html',
  styleUrls: ['./main-display.component.css']
})
export class MainDisplayComponent implements OnInit, OnDestroy {

  themesList: ComplainThemeModel[];
  themesSubscription: Subscription;

  requestsList: ComplainRequestModel[];
  requestsSubscription: Subscription;

  constructor(private complainThemeService: ComplainThemeService,
              private complainRequestService: ComplainRequestService) { }

  ngOnInit() {
    // subscription
    this.themesSubscription = this.complainThemeService.themeSubject.subscribe(
      (themes: ComplainThemeModel[]) => {
        this.themesList = themes;
      }
    );
    this.complainThemeService.getAllThemes(() => {
      this.complainThemeService.emitThemes();
      // test
      console.log('themes: ' + this.themesList[0].name);
    });

    this.requestsSubscription = this.complainRequestService.requestSubject.subscribe(
      (requests: ComplainRequestModel[]) => {
        this.requestsList = requests;
      }
    );
    this.complainRequestService.getAllRequests(() => {
      this.complainRequestService.emitRequests();
    });

  }

  ngOnDestroy() {
    this.themesSubscription.unsubscribe();
    this.requestsSubscription.unsubscribe();
  }

}
