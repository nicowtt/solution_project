export class ComplainRequestModel {

    id: string;
    request: string;
    complainUserId: number;
    creatorPseudo: string;
    creatorEmail: string;
    creationDate: string;
    creationDayUntilToday: number;
    popularity: number;
    nbrResponse: number;
    themeName: string;
    complainResponsesId: string;
    lastResponseDate: number;

    constructor() {}
}
