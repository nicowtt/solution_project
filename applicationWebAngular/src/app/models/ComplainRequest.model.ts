import { ComplainResponseModel } from './ComplainResponse.model';
export class ComplainRequestModel {

    id: number;
    request: string;
    complainUserId: number;
    creationDate: string;
    popularity: number;
    nbrResponse: number;
    themeName: string;
    complainResponses: ComplainResponseModel[];

    constructor() {}
}
