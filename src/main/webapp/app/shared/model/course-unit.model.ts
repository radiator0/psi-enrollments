import { ICourse } from 'app/shared/model/course.model';

export interface ICourseUnit {
  id?: number;
  code?: string;
  ects?: number;
  isGroupOfCourses?: boolean;
  isStream?: boolean;
  isSelectable?: boolean;
  courses?: ICourse[];
  selectableModuleId?: number;
}

export const defaultValue: Readonly<ICourseUnit> = {
  isGroupOfCourses: false,
  isStream: false,
  isSelectable: false
};
