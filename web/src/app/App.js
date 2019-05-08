import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch,
  Link
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import Login from '../user/login/Login';
import Report from '../pages/Report'
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';
import ProductShowList from '../pages/Product/Product';
import EmployeeList from '../pages/Employee/Employee'
import uploadPage from '../pages/ImageUpload';

import { Layout, notification, Menu, Icon } from 'antd';

const { Content, Sider } = Layout;


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      collapsed: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    this.handlerToggleSlider = this.handlerToggleSlider.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }


  handlerToggleSlider() {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
      .then(response => {

        console.log(response.role)
        // if(response.role === "[ROLE_USER]")
        //   console.log('YES')
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      }).catch(error => {
        this.setState({
          isLoading: false
        });
      });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: 'POS System',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'POS System',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />
    }

    if (this.state.isAuthenticated && this.state.currentUser.role === '[ROLE_ADMIN]') {
      return (

        <Layout className="app-container">
          <Sider
            className="slider-bar"
            trigger={null}
            collapsible
            collapsed={this.state.collapsed}>
            <div className="logo">
              <span style={{ color: '#267ac5', fontSize: 22 }}>POS </span>
              <span style={{ color: '#339fff' }}>System</span>
            </div>
            <Menu mode="inline" defaultSelectedKeys={['1']}>
              <Menu.Item key="1">
                <Icon type="dashboard" />
                <span>Dashboard</span>
                <Link to="/" />
              </Menu.Item>
              <Menu.Item key="2">
                <Icon type="user" />
                <span>จัดการพนักงาน</span>
                <Link to="/employee" />
              </Menu.Item>
              <Menu.Item key="3">
                <Icon type="folder" />
                <span>สต็อกสินค้า</span>
              </Menu.Item>
              <Menu.Item key="4">
                <Icon type="appstore" />
                <span>จัดการสินค้า</span>
                <Link to="/Products" />
              </Menu.Item>
            </Menu>
          </Sider>
          <Layout>
            <AppHeader isAuthenticated={this.state.isAuthenticated}
              currentUser={this.state.currentUser}
              onLogout={this.handleLogout}
              collapsed={this.collapsed}
              handlerToggleSlider={this.handlerToggleSlider} />
            <Content className="app-content">
              <div className="container">
                <Switch>
                  {/* <Route exact path="/"
                    render={(props) => <PollList isAuthenticated={this.state.isAuthenticated}
                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                  </Route> */}
                  <Route exact path="/"
                    render={(props) => <Report isAuthenticated={this.state.isAuthenticated}
                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                  </Route>
                  <PrivateRoute authenticated={this.state.isAuthenticated} path="/employee" component={EmployeeList} handleLogout={this.handleLogout}></PrivateRoute>
                  <PrivateRoute authenticated={this.state.isAuthenticated} path="/Products" component={ProductShowList} handleLogout={this.handleLogout}></PrivateRoute>
                  <Route component={NotFound}></Route>
                </Switch>
              </div>
            </Content>
          </Layout>
        </Layout>
      );

    } else if (this.state.isAuthenticated && this.state.currentUser.role === '[ROLE_USER]') {

      return (
        <Layout className="app-container">
          <Sider
            className="slider-bar"
            trigger={null}
            collapsible
            collapsed={this.state.collapsed}>
            <div className="logo">
              <span style={{ color: '#267ac5', fontSize: 22 }}>POS </span>
              <span style={{ color: '#339fff' }}>System</span>
            </div>
            <Menu mode="inline" defaultSelectedKeys={['1']}>
              <Menu.Item key="1">
                <Icon type="credit-card" />
                <span>จัดการใบสั่งซื้อ</span>
                <Link to="/" />
              </Menu.Item>
            </Menu>
          </Sider>
          <Layout>
            <AppHeader isAuthenticated={this.state.isAuthenticated}
              currentUser={this.state.currentUser}
              onLogout={this.handleLogout}
              collapsed={this.collapsed}
              handlerToggleSlider={this.handlerToggleSlider} />
            <Content className="app-content">
              <div className="container">
                <Switch>
                  {/* <Route exact path="/"
                  render={(props) => <PollList isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                </Route> */}
                  <Route exact path="/"
                    render={() => <h1>Bill.. Form</h1>}>
                  </Route>
                  <Route component={NotFound}></Route>
                </Switch>
              </div>
            </Content>
          </Layout>
        </Layout>
      );

    } else {
      return (
        <div>
          <Login onLogin={this.handleLogin} />
        </div>
      );


    }

  }
}

export default withRouter(App);
