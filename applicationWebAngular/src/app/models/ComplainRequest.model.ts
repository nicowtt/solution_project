import { BottleModel } from './Bottles.model';

export class ComplainRequestModel {

    id: string;
    request: string;
    complainUserId: number;
    creatorPseudo: string;
    creatorEmail: string;
    creationDate: string;
    creationDaysUntilToday: number;
    creationHoursUntilToday: number;
    creationMinutesUntilToday: number;
    popularity: number;
    userWhoChangePopularityList: string[];
    nbrResponse: number;
    themeName: string;
    complainResponsesId: string;
    lastResponseDate: string;
    lastReponseDaysUntilToday: number;
    lastReponseHoursUntilToday: number;
    lastReponseMinutesUntilToday: number;
    bottle: BottleModel;
    forgotten: boolean;

    constructor() {}

    toString() {
      return '{ ' +
              'request: ' + this.request +
              ' complainUserId: ' + this.complainUserId +
              '}';
    }


}
