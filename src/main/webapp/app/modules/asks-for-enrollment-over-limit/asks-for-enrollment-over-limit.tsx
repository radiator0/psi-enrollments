import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type IAsksForEnrollmentOverLimitProps = StateProps;

class AsksForEnrollmentOverLimit extends Component<IAsksForEnrollmentOverLimitProps> {
  render() {
    return (
      <Row>
        Asks for enrollment over limit
      </Row>
    );
  }
}

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(AsksForEnrollmentOverLimit);
