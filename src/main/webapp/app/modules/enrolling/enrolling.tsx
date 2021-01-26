import React, { Component } from 'react';
import { connect } from 'react-redux';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import CourseList from './view/courses/course-list';
import axios from 'axios';
import log from 'app/config/log';
import { RouteComponentProps } from 'react-router-dom';
import CoursesData from './courses-data';
import mapSelectableCourseBlockToCoursesData from './domain/mapper/selectable-course-block-to-courses-data-mapper';
import mapEnrollingGroupDetailsToGroupsData from './domain/mapper/enrolling-group-details-to-groups-data-mapper';
import CourseDetails from '../../shared/model/domain/dto/course-details';
import SelectableCourseBlockDetails from 'app/shared/model/domain/dto/selectable-course-block-details';
import GroupsData from './groups-data';
import GroupList from './view/groups/group-list';
import EnrollingGroupDetails from '../../shared/model/domain/dto/enrolling-group-details';
import { StaticContext } from 'react-router';
import EnrollmentData from '../enrollments/enrollment-data';
import { EnrollingAction } from './enrolling-action';
import { toast } from 'react-toastify';
import { Translate, translate } from 'react-jhipster';
import CourseUnitDetails from 'app/shared/model/domain/dto/course-unit-details';

export type IEnrollingProps = RouteComponentProps<{ }, StaticContext, { enrollment: EnrollmentData }>;


interface IEnrollingState {
  coursesData: Array<CoursesData>
  selectedCourse: CourseDetails,
  groupsData: Array<GroupsData>
};

const gridStyle = {
  display: 'flex',
};

const courseListStyle = {
  width: '30%',
};

const groupsListStyle = {
  width: '70%', 
};

class Enrolling extends Component<IEnrollingProps, IEnrollingState> {
  constructor(props: IEnrollingProps) {
    super(props);

    this.state = {
      coursesData: [],
      selectedCourse: null,
      groupsData: []
    };
  }

  componentDidMount() {
    if(this.props.history.location.state.enrollment) {
      this.getCoursesData();
    }
  }

  refresh() {
    this.getCoursesData();
    this.getGroupsData(this.state.selectedCourse.id);
  }

  onCourseSelected(course: CourseDetails) {
    this.getGroupsData(course.id)
    this.setState( { selectedCourse: course } )
  }

  enroll(group: GroupsData) {
    log.info(this.getGroupsToDisenrollFromBeforeEnrolling(group));

    axios.post(`/api/enrolling`, { id: group.id })
    .then(r => {
      toast.success(translate("enrolling.notification.enrolled"))
    })
    .catch(e => {
      log.error(e);
      toast.error(translate("enrolling.notification.enrollFailure"))
    })
    .finally(() => {
      this.refresh();
    });
  }

  getGroupsToDisenrollFromBeforeEnrolling(groupToEnrollTo: GroupsData) {
    const flatMap : (a : Array<CourseUnitDetails>, f : (cu: CourseUnitDetails) => Array<CourseDetails>) => Array<CourseDetails>
      = (xs, f) => [].concat(...xs.map(f))

    const { coursesData, selectedCourse, groupsData } = this.state;
    const selectableCourseBlock = coursesData.map(cd => cd.selectableCourseBlocks)
      .find(s => s.courseUnits.some(cu => cu.courses.some(c => c === selectedCourse)));
    const courseUnit = selectableCourseBlock.courseUnits.find(cu => cu.courses.some(c => c === selectedCourse));

    const isAlreadyEnrolledInThisCourse = groupsData.some(g => g.isStudentEnrolled);

    const otherCoursesToDisenroll = flatMap(selectableCourseBlock.courseUnits.filter(cu => cu !== courseUnit), cu => cu.courses).find(c => c.studentEnrolled);

    return { isAlreadyEnrolledInThisCourse, otherCoursesToDisenroll };
  }

  disenroll(group: GroupsData) {
    axios.delete(`/api/enrolling`, { data: { id: group.id } })
    .then(r => {
      toast.success(translate("enrolling.notification.disenrolled"))
    })
    .catch(e => {
      log.error(e);
      toast.error(translate("enrolling.notification.disenrollFailure"))
    })
    .finally(() => {
      this.refresh();
    });
  }

  askOverLimit(group: GroupsData) {

  }

  recallAskOverLimit(group: GroupsData) {

  }

  onGroupSelected(group: GroupsData, action: EnrollingAction) {
    switch(action) {
      case EnrollingAction.Enroll:
        this.enroll(group);
        break;
      case EnrollingAction.Disenroll:
        this.disenroll(group);
        break;
      case EnrollingAction.AskOverLimit:
        this.askOverLimit(group);
        break;
      case EnrollingAction.RecallAsk:
        this.recallAskOverLimit(group);
        break;
      default:
        log.error('no action');
    }
  }

  getCoursesData() {
    const { enrollment } = this.props.history.location.state;
    axios.get<Array<SelectableCourseBlockDetails>>(`/api/enrollment/${enrollment.id}/selectable-modules`)
    .then(r => {
      const data = r.data.map(element => mapSelectableCourseBlockToCoursesData(element));
      this.setState({ coursesData: data })
    })
    .catch(e => log.error(e))
    // this.setState({ coursesData: fakeData });
  }

  getGroupsData(id: number) {
    axios.get<Array<EnrollingGroupDetails>>(`/api/course/${id}/groups`)
    .then(r => {
      const data = r.data.map(element => mapEnrollingGroupDetailsToGroupsData(element));
      this.setState({ groupsData: data })
      log.info('updating group list')
    })
    .catch(e => log.error(e))
  }

  renderHeader() {
    return (
      <Typography variant='h4' component='h4' align='center'>
        <Translate contentKey={'enrolling.header.enrollments'}>Enrollments</Translate>
      </Typography>
    );
  }

  renderCoursesList() {
    return (
      <CourseList coursesData={this.state.coursesData} onSelected={this.onCourseSelected.bind(this)}></CourseList>
    );
  }

  renderGroupsTable() {
    return (
      <GroupList groupsData={this.state.groupsData} enrollment={this.props.history.location.state.enrollment} onSelected={this.onGroupSelected.bind(this)}></GroupList>
    );
  }

  render() {
    return (
      <>
        {this.renderHeader()}
        <Grid style={gridStyle}>
          <div style={courseListStyle}>
            {this.renderCoursesList()}
          </div>
          <div style={groupsListStyle}>
            {this.renderGroupsTable()}
          </div>
        </Grid>
      </>
    );
  }
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Enrolling);
