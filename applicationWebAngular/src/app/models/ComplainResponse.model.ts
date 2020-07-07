export class ComplainResponseModel {

    id: number;
    response: string;
    creationDate: string;
    dayUntilToday: number;
    popularity: number;
    creatorEmail: string;
    creatorPseudo: string;
    complainUserId: number;
    complainRequestId: number;

    constructor() {}
}
