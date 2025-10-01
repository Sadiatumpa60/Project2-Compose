import jenkins.model.*
import hudson.security.*

def j = Jenkins.get()

def realm = new HudsonPrivateSecurityRealm(false)
def pw = System.getenv('PIPELINE_USER_PASSWORD') ?: 'ChangeMe123!'
if (realm.getUser('pipeline') == null) {
    realm.createAccount('pipeline', pw)
}
j.setSecurityRealm(realm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
j.setAuthorizationStrategy(strategy)

j.save()
println "Security applied: 'pipeline' user created, anonymous access disabled."
