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

export type IEnrollingProps = RouteComponentProps<{ }, StaticContext, { enrollment: EnrollmentData }>;


interface IEnrollingState {
  coursesData: Array<CoursesData>
  groupsData: Array<GroupsData>
};

const gridStyle = {
  display: 'flex',
};

const courseListStyle = {
  width: '25%',
};

const groupsListStyle = {
  width: '75%', 
};

class Enrolling extends Component<IEnrollingProps, IEnrollingState> {
  constructor(props: IEnrollingProps) {
    super(props);

    this.state = {
      coursesData: [],
      groupsData: []
    };
  }

  componentDidMount() {
    log.info(this.props);
    if(this.props.history.location.state.enrollment) {
      this.getCoursesData();
    }
  };

  onCourseSelected(course: CourseDetails) {
    this.getGroupsData(course.id)
  }

  onGroupSelected(group: GroupsData) {
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
    })
    .catch(e => log.error(e))
  }

  renderHeader() {
    return (
      <Typography variant='h4' component='h4' align='center'>
        Zapisy
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
