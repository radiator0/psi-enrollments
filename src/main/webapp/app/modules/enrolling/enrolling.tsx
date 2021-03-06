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
import { logInfo, Translate, translate } from 'react-jhipster';
import CourseUnitDetails from '../../shared/model/domain/dto/course-unit-details';
import Modal from '../../shared/layout/modal';
import { createEntity, getEntities, deleteEntity } from 'app/shared/reducers/request.reducer';
import { IRootState } from 'app/shared/reducers';
import { IRequest } from 'app/shared/model/request.model';
import { IconButton } from '@material-ui/core';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';

export interface IEnrollingProps extends StateProps, DispatchProps, RouteComponentProps<{}, StaticContext, { enrollment: EnrollmentData }> { };


interface IEnrollingState {
  coursesData: Array<CoursesData>
  selectedCourse: CourseDetails,
  groupsData: Array<GroupsData>,
  modal: () => any
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

interface IModalOption {
  optionNameKey: string,
  onClick: (text: string | null) => void
}

const confirmModalOption = (onClick: (text: string | null) => void) => { return { optionNameKey: 'enrolling.modal.confirm', onClick } };
const cancelModalOption = (onClick: (text: string | null) => void) => { return { optionNameKey: 'enrolling.modal.cancel', onClick } };
const confirmAndEnrollModalOption = (onClick: () => void) => { return { optionNameKey: 'enrolling.modal.confirmAndEnroll', onClick } };

class Enrolling extends Component<IEnrollingProps, IEnrollingState> {
  constructor(props: IEnrollingProps) {
    super(props);

    this.state = {
      coursesData: [],
      selectedCourse: null,
      groupsData: [],
      modal: () => <></>
    };
  }

  componentDidMount() {
    if (this.props.history.location.state.enrollment) {
      this.getCoursesData();
      this.props.getEntities();
    }
  }

  refresh() {
    this.getCoursesData();
    this.getGroupsData(this.state.selectedCourse.id);
    this.props.getEntities();
  }

  showModal(title: string, content: string, options: Array<IModalOption>, closeOnEveryOption: boolean, textField: { placeholderText: string }) {
    if (closeOnEveryOption) {
      options = options.map(o => { return { optionNameKey: o.optionNameKey, onClick: (text) => { this.closeModal(); o.onClick(text) } } })
    }
    this.setState({ modal: () => <Modal title={title} content={content} options={options} textField={textField}></Modal> });
  }

  closeModal() {
    this.setState({ modal: () => <></> });
  }

  onCourseSelected(course: CourseDetails) {
    this.getGroupsData(course.id)
    this.setState({ selectedCourse: course })
  }

  onEnrollClick(group: GroupsData) {
    log.info('clicked to enroll to course:');
    log.info(this.state.selectedCourse);

    const collision = this.getGroupsToDisenrollFromBeforeEnrolling(group);

    if (collision.isAlreadyEnrolledInThisCourse || collision.otherCoursesToDisenroll?.length > 0) {
      const modalTitle = translate('enrolling.modal.attention');
      const modalContent = this.createCollisionMessage(collision);

      this.showModal(modalTitle, modalContent, [cancelModalOption(() => { }), confirmModalOption(() => this.enroll(group))], true, null);
    }
    else {
      this.enroll(group);
    }
  }

  createCollisionMessage(collision: { isAlreadyEnrolledInThisCourse, otherCoursesToDisenroll }) {
    let modalContent = '';
    if (collision.isAlreadyEnrolledInThisCourse) {
      modalContent += translate('enrolling.modal.enrolledInThisCourseAlready');
    }
    if (collision.otherCoursesToDisenroll?.length > 0) {
      modalContent += translate('enrolling.modal.disenrollingNecessary');
      collision.otherCoursesToDisenroll.forEach((v) => modalContent += `\n${v.shortName || v.name} (${translate(`enrollmentsApp.ClassType.${v.form}`)})`)
    }
    return modalContent;
  }

  enroll(group: GroupsData) {
    axios.post(`/api/enrolling`, { id: group.id })
      .then(r => {
        toast.success(translate("enrolling.notification.enrolled"))
      })
      .catch(e => {
        log.error(e);
        toast.error(translate("enrolling.notification.enrollFailure"));
      })
      .finally(() => {
        this.refresh();
      });
  }

  getGroupsToDisenrollFromBeforeEnrolling(group: GroupsData) {
    const flatMap: (a: Array<CourseUnitDetails>, f: (cu: CourseUnitDetails) => Array<CourseDetails>) => Array<CourseDetails>
      = (xs, f) => [].concat(...xs.map(f))

    const { coursesData, selectedCourse, groupsData } = this.state;
    const selectableCourseBlock = coursesData.map(cd => cd.selectableCourseBlocks)
      .find(s => s.courseUnits.some(cu => cu.courses.some(c => c.id === selectedCourse.id)));
    const courseUnit = selectableCourseBlock.courseUnits.find(cu => cu.courses.some(c => c.id === selectedCourse.id));

    const isAlreadyEnrolledInThisCourse = groupsData.some(g => g.isStudentEnrolled && g.id !== group.id);

    const otherCoursesToDisenroll = flatMap(selectableCourseBlock.courseUnits.filter(cu => cu !== courseUnit), cu => cu.courses).filter(c => c.studentEnrolled);

    return { isAlreadyEnrolledInThisCourse, otherCoursesToDisenroll };
  }

  onDisenrollClick(group: GroupsData) {
    this.showModal(translate('enrolling.modal.attention'), translate('enrolling.modal.confirmDisenrollment'),
      [cancelModalOption(() => { }), confirmModalOption(() => this.disenroll(group))], true, null)
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

  createPlaceholderRequestText() {
    const { firstName, lastName } = this.props.account;
    return `${translate('enrolling.modal.placeholderRequest')}${firstName} ${lastName}`;
  }

  onAskOverLimitClick(group: GroupsData) {
    this.showModal(translate('enrolling.modal.ask'), translate('enrolling.modal.typeAskForEnrollment'),
      [cancelModalOption(() => { }), confirmModalOption((text) => this.askOverLimit(group, text))], true, { placeholderText: this.createPlaceholderRequestText() })
  }

  askOverLimit(group: GroupsData, text: string) {
    const askOverLimit: IRequest = {
      classGroupId: group.id,
      text
    };
    this.props.createEntity(askOverLimit);
  }

  onRecallAskOverLimitClick(group: GroupsData) {
    const askOverLimit = this.props.asksOverLimit.find(ask => ask.classGroupId === group.id);
    if (!group.isLimitReached) {
      // we can enroll!
      const collisionText = this.createCollisionMessage(this.getGroupsToDisenrollFromBeforeEnrolling(group))
      this.showModal(translate('enrolling.modal.attention'), `${translate('enrolling.modal.placeInGroup')}${collisionText.length === 0 ? '' : '\n'}\n${collisionText}`,
        [cancelModalOption(() => { }), confirmAndEnrollModalOption(() => this.enroll(group)),
        confirmModalOption(() => this.recallAskOverLimit(askOverLimit))], true, null)
    }
    else {
      this.showModal(translate('enrolling.modal.attention'), translate('enrolling.modal.confirmAskForEnrollmentRecall'),
        [cancelModalOption(() => { }), confirmModalOption(() => this.recallAskOverLimit(askOverLimit))], true, null)
    }
  }

  recallAskOverLimit(askOverLimit: IRequest) {
    this.props.deleteEntity(askOverLimit.id);
  }

  onGroupSelected(group: GroupsData, action: EnrollingAction) {
    switch (action) {
      case EnrollingAction.Enroll:
        this.onEnrollClick(group);
        break;
      case EnrollingAction.Disenroll:
        this.onDisenrollClick(group);
        break;
      case EnrollingAction.AskOverLimit:
        this.onAskOverLimitClick(group);
        break;
      case EnrollingAction.RecallAsk:
        this.onRecallAskOverLimitClick(group);
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
      <CourseList coursesData={this.state.coursesData} onSelected={this.onCourseSelected.bind(this)} selectedCourse={this.state.selectedCourse}></CourseList>
    );
  }

  renderGroupsTable() {
    return (
      <GroupList
        groupsData={this.state.groupsData}
        enrollment={this.props.history.location.state.enrollment}
        onSelected={this.onGroupSelected.bind(this)}
        requestOverLimit={this.props.asksOverLimit}
      />
    );
  }

  render() {
    return (
      <>
        {this.state.modal()}
        <IconButton onClick={() => this.props.history.goBack()} style={{position: 'absolute'}}>
          <ArrowBackIcon />
        </IconButton>
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

const mapStateToProps = ({ authentication, request }: IRootState) => ({
  account: authentication.account,
  asksOverLimit: request.entities,
});

const mapDispatchToProps = {
  createEntity,
  getEntities,
  deleteEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Enrolling);
