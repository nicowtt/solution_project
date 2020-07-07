import { SubscriptionThemeUserModel } from './SubscriptionThemeUser.model';

export class ComplainThemeModel {

  id: number;
  name: string;
  photoUrl: string;
  creatorEmail: string;
  creationDate: string;
  popularity: number;
  complainRequestsId: string;
  subscriptionThemeUsers: SubscriptionThemeUserModel[];

  constructor() {}
}
