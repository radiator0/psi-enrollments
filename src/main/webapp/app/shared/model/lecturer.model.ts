import { IClassGroup } from 'app/shared/model/class-group.model';

export interface ILecturer {
  id?: number;
  firstName?: string;
  secondName?: string;
  lastName?: string;
  mail?: string;
  title?: string;
  classGroups?: IClassGroup[];
}

export const defaultValue: Readonly<ILecturer> = {};
