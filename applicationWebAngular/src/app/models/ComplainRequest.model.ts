import { ComplainResponseModel } from './ComplainResponse.model';
export class ComplainRequestModel {

    id: number;
    request: string;
    complainUserId: number;
    creatorPseudo: string;
    creatorEmail: string;
    creationDate: string;
    dayUntilToday: number;
    popularity: number;
    nbrResponse: number;
    themeName: string;
    complainResponses: ComplainResponseModel[];

    constructor() {}
}
