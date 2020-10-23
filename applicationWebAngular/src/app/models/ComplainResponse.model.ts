export class ComplainResponseModel {

    id: string;
    response: string;
    creationDate: string;
    creationDaysUntilToday: number;
    creationHoursUntilToday: number;
    creationMinutesUntilToday: number;
    popularity: number;
    creatorEmail: string;
    creatorPseudo: string;
    complainUserId: number;
    requestId: string;
    commentList: string[];

    constructor() { }
}
