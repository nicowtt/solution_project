import { ComplainCommentModel } from './ComplainComment.model';

export class ComplainResponseModel {

    id: string;
    response: string;
    extLink: string;
    creationDate: string;
    creationDaysUntilToday: number;
    creationHoursUntilToday: number;
    creationMinutesUntilToday: number;
    popularity: number;
    userWhoChangePopularityList: string[];
    creatorEmail: string;
    creatorPseudo: string;
    complainUserId: number;
    requestId: string;
    commentList: ComplainCommentModel[];
    totalComment: number;

    constructor() { }
}
