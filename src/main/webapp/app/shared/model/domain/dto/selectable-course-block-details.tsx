import CourseDetails from './course-details';
import TogetherCourseBlockDetails from './together-course-block-details';

export default class SelectableCourseBlockDetails {
  public readonly id: number;
  public readonly name: string;
  public readonly togetherBlocks: Array<TogetherCourseBlockDetails>;

  constructor(id: number, name: string, togetherBlocks: Array<TogetherCourseBlockDetails>) {
    this.id = id;
    this.name = name;
    this.togetherBlocks = togetherBlocks;
  }
};