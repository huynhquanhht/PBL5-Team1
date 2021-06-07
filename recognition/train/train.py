
from config.config import get_config
from Learner import learner


if __name__ == '__main__':
    conf = get_config(training=True)
    learner = learner(conf)
    learner.train(conf, conf.epochs)