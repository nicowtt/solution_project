export class ComplainResponseModel {

    id: number;
    response: string;
    creationDate: string;
    popularity: number;
    creatorEmail: string;
    creatorPseudo: string;
    complainUserId: number;
    complainRequestId: number;

    constructor() {}
}
