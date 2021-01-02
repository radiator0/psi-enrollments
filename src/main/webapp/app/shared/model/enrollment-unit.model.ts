import { Moment } from 'moment';

export interface IEnrollmentUnit {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  enrollmentDateId?: number;
}

export const defaultValue: Readonly<IEnrollmentUnit> = {};
