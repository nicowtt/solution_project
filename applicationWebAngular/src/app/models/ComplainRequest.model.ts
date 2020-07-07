export class ComplainRequestModel {

    id: string;
    request: string;
    complainUserId: number;
    creatorPseudo: string;
    creatorEmail: string;
    creationDate: string;
    dayUntilToday: number;
    popularity: number;
    nbrResponse: number;
    themeName: string;
    complainResponsesId: string;

    constructor() {}
}
