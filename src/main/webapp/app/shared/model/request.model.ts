import { Moment } from 'moment';

export interface IRequest {
  id?: number;
  uuid?: string;
  date?: Moment;
  text?: string;
  isExamined?: boolean;
  classGroupId?: number;
  studentId?: number;
}

export const defaultValue: Readonly<IRequest> = {
  isExamined: false
};
