export class ComplainResponseModel {

    id: string;
    response: string;
    creationDate: string;
    dayUntilToday: number;
    popularity: number;
    creatorEmail: string;
    creatorPseudo: string;
    complainUserId: number;
    complainRequestId: string;

    constructor() {}
}
