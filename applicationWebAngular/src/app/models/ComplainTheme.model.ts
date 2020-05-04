import { SubscriptionThemeUserModel } from './SubscriptionThemeUser.model';
import { ComplainRequestModel } from './ComplainRequest.model';

export class ComplainThemeModel {

  id: number;
  name: string;
  photoUrl: string;
  creatorEmail: string;
  creationDate: Date;
  popularity: number;
  complainRequests: ComplainRequestModel[];
  subscriptionThemeUsers: SubscriptionThemeUserModel[];

  constructor() {}
}
