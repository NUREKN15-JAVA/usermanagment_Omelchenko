package ua.nure.kn155.omelchenko.agent;

import java.util.Collection;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.DatabaseException;
import ua.nure.kn155.omelchenko.gui.SearchGui;

public class SearchAgent extends Agent {

	private static final long messageCounter = 60000;
	private AID[] aids;
	SearchGui gui = null;

	@Override
	protected void setup() {
		super.setup();
		System.out.println(getAID().getName() + "is started");
		gui = new SearchGui(this);
		gui.setVisible(true);

		DFAgentDescription description = new DFAgentDescription();
		description.setName(getAID());

		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName("JADE-searching");
		serviceDescription.setType("searching");
		description.addServices(serviceDescription);
		try {
			DFService.register(this, description);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		addBehaviour(new TickerBehaviour(this, messageCounter) {
			@Override
			protected void onTick() {
				DFAgentDescription agentDescription = new DFAgentDescription();
				ServiceDescription serviceDescription = new ServiceDescription();
				serviceDescription.setType("searching");
				agentDescription.addServices(serviceDescription);
				try {
					DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
					aids = new AID[descriptions.length];
					for (int i = 0; i < descriptions.length; i++) {
						aids[i] = descriptions[i].getName();
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
		addBehaviour(new RequestServer());
	}

	@Override
	protected void takeDown() {
		System.out.println(getAID().getName() + "is terminated.");
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		gui.setVisible(false);
		gui.dispose();
		super.takeDown();///////////////////////////////
	}

	public void search(String firstName, String lastName) throws SearchException {
		try {
			Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			if (!users.isEmpty()) {
				showUsers(users);
			} else {
				addBehaviour(new SearchRequestBehavior(aids, firstName, lastName));
			}
		} catch (DatabaseException e) {
			throw new SearchException(e);
		}
	}

	void showUsers(Collection<User> users) {
		gui.addUsers(users);

	}
}
