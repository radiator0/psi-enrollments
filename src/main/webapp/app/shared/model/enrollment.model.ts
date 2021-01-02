import { Moment } from 'moment';

export interface IEnrollment {
  id?: number;
  date?: Moment;
  isAdministrative?: boolean;
  studentId?: number;
  classGroupId?: number;
}

export const defaultValue: Readonly<IEnrollment> = {
  isAdministrative: false
};
