export class ComplainRequestModel {

    id: number;
    request: string;
    complainUserId: number;
    creationdate: Date;
    popularity: number;
    nbrResponse: number;
    themeId: number;

    constructor() {}
}
