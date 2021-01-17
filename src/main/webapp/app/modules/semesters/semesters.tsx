import React from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type ISemestersProps = StateProps;

export const Semesters = (props: ISemestersProps) => {

  return (
    <Row>
      Semesters
    </Row>
  );
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Semesters);
