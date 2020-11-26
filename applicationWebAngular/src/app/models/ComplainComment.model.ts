
export class ComplainCommentModel {

  id: string;
  comment: string;
  creatorPseudo: string;
  creatorEmail: string;
  creationDate: string;
  responseId: string;
  creationDaysUntilToday: number;
  creationHoursUntilToday: number;
  creationMinutesUntilToday: number;

  constructor() {}

  toString() {
    return '{' +
           'id: ' +
           ' comment: ' +
           ' creator Pseudo: ' +
           ' creator Email: ' +
           ' creation Date: ' +
           ' response Id: ' +
           '}';
  }
}
