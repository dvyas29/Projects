import React from 'react'
import Signup from "./Signup"
import Login from "./Login"
import Homepage from "./Homepage"
import Resetpassword from "./Resetpassword"
import AuthProvider from '../contexts/AuthContext';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Securitypage from './Securitypage'
import ChatBox from './Chat'
import Subway from './Subway'
import Mcd from './Mcd'

function App() {
  return (
    <Router>
      <AuthProvider>
        <Switch>
          {/* <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: "100vh" }}>
            <div className="w-100" style={{ maxWidth: "400px" }}> */}
          <Route exact path="/" component={Login} />
          <Route path="/signup" component={Signup} />
          <Route path="/reset-password" component={Resetpassword} />
          <Route path="/security-page" component={Securitypage} />
          {/* </div>
          </Container> */}
          <Route path="/homepage" component={Homepage} />
          <Route path="/chat" component={ChatBox} />
          <Route path="/mcd" component={Mcd} />
          <Route path="/subway" component={Subway} />
        </Switch>
      </AuthProvider>
    </Router>
  )
}

export default App;
