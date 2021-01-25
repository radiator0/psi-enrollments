import CourseDetails from './course-details';

export default class CourseUnitDetails {
  public readonly id: number;
  public readonly isStream: boolean;
  public readonly isGroupOfCourses: boolean;
  public readonly code: string;
  public readonly ects: number;
  public readonly courses: Array<CourseDetails>;

  constructor(id: number, isStream: boolean, isGroupOfCourses: boolean, code: string, ects: number, courses: Array<CourseDetails>) {
    this.id = id;
    this.isStream = isStream;
    this.isGroupOfCourses = isGroupOfCourses;
    this.code = code;
    this.ects = ects;
    this.courses = courses;
  }
};