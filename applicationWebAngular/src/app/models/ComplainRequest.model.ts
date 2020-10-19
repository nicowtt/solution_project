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
    nbrResponse: number;
    themeName: string;
    complainResponsesId: string;
    lastResponseDate: number;

    constructor() {}

    toString() {
      return '{ ' +
              'request: ' + this.request +
              ' complainUserId: ' + this.complainUserId +
              '}';
    }


}
