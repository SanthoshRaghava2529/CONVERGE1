import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ExperienceCard from "../components/ExperienceCard";
import api from "../services/api";

function Home() {
  const [experiences, setExperiences] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const response = await api.get("/experiences");
        setExperiences(response.data.slice(0, 8));
      } catch (err) {
        setError(
          err.response?.data?.message ||
            "Unable to load featured experiences."
        );
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  return (
    <main>
      <section className="relative overflow-hidden bg-slate-950">
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_20%_20%,rgba(99,102,241,0.35),transparent_35%),radial-gradient(circle_at_80%_70%,rgba(168,85,247,0.25),transparent_35%)]" />

        <div className="relative mx-auto max-w-7xl px-6 py-24 md:py-32">
          <div className="max-w-3xl">
            <span className="rounded-full border border-indigo-400/30 bg-indigo-400/10 px-4 py-2 text-sm font-semibold text-indigo-300">
              Entertainment discovery, reimagined
            </span>

            <h1 className="mt-7 text-5xl font-black leading-tight tracking-tight text-white md:text-7xl">
              Find what
              <span className="block text-indigo-400">everyone agrees on.</span>
            </h1>

            <p className="mt-6 max-w-2xl text-lg leading-8 text-slate-300">
              Discover movies, events, sports and theatre. Planning with
              friends? CONVERGE finds experiences that match the whole group.
            </p>

            <div className="mt-9 flex flex-wrap gap-4">
              <Link
                to="/explore"
                className="rounded-xl bg-indigo-600 px-6 py-3.5 font-bold text-white shadow-lg shadow-indigo-600/20 hover:bg-indigo-500"
              >
                Explore Experiences
              </Link>
              <Link
                to="/groups"
                className="rounded-xl border border-slate-700 bg-white/5 px-6 py-3.5 font-bold text-white hover:bg-white/10"
              >
                Start Group Converge
              </Link>
            </div>
          </div>
        </div>
      </section>

      <section className="mx-auto max-w-7xl px-6 py-16">
        <div className="flex items-end justify-between">
          <div>
            <p className="text-sm font-bold uppercase tracking-wider text-indigo-600">
              Discover
            </p>
            <h2 className="mt-2 text-3xl font-black text-slate-900">
              Popular experiences
            </h2>
          </div>
          <Link
            to="/explore"
            className="text-sm font-bold text-indigo-600 hover:text-indigo-700"
          >
            View all →
          </Link>
        </div>

        {loading && (
          <div className="py-16 text-center text-slate-500">
            Loading featured experiences...
          </div>
        )}

        {error && (
          <div className="mt-8 rounded-2xl border border-red-200 bg-red-50 p-6 text-red-700">
            {error}
          </div>
        )}

        {!loading && !error && experiences.length === 0 && (
          <div className="mt-8 rounded-2xl border border-dashed border-slate-300 py-16 text-center text-slate-500">
            No experiences are available yet.
          </div>
        )}

        <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {experiences.map((experience) => (
            <ExperienceCard key={experience.id} experience={experience} />
          ))}
        </div>
      </section>

      <section className="mx-auto max-w-7xl px-6 pb-20">
        <div className="overflow-hidden rounded-3xl bg-indigo-600 p-8 text-white md:p-12">
          <p className="text-sm font-bold uppercase tracking-wider text-indigo-200">
            The CONVERGE difference
          </p>
          <h2 className="mt-3 max-w-2xl text-3xl font-black md:text-4xl">
            Your group doesn't need another argument.
          </h2>
          <p className="mt-4 max-w-2xl leading-7 text-indigo-100">
            Everyone shares their preferences. CONVERGE calculates the
            strongest common options and explains why they match.
          </p>
          <Link
            to="/groups"
            className="mt-7 inline-block rounded-xl bg-white px-6 py-3 font-bold text-indigo-700 hover:bg-indigo-50"
          >
            Try Group Converge
          </Link>
        </div>
      </section>
    </main>
  );
}

export default Home;
