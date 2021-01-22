import CourseDetails from './course-details';

export default class TogetherCourseBlockDetails {
  public readonly id: number;
  public readonly name: string;
  public readonly courses: Array<CourseDetails>;

  constructor(id: number, name: string, courses: Array<CourseDetails>) {
    this.id = id;
    this.name = name;
    this.courses = courses;
  }
};