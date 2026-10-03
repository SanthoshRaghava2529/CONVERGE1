import { useEffect, useMemo, useState } from "react";
import ExperienceCard from "../components/ExperienceCard";
import api from "../services/api";

function Explore() {
  const [experiences, setExperiences] = useState([]);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("All");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadExperiences = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await api.get("/experiences");

        setExperiences(response.data);
      } catch (err) {
        console.error(err);

        setError(
          err.response?.data?.message ||
            "Unable to load experiences from the server."
        );
      } finally {
        setLoading(false);
      }
    };

    loadExperiences();
  }, []);

  const filteredExperiences = useMemo(() => {
    return experiences.filter((experience) => {
      const text = search.toLowerCase();

      const matchesSearch =
        experience.title?.toLowerCase().includes(text) ||
        experience.genre?.toLowerCase().includes(text) ||
        experience.language?.toLowerCase().includes(text) ||
        experience.city?.toLowerCase().includes(text);

      const matchesCategory =
        category === "All" || experience.category === category;

      return matchesSearch && matchesCategory;
    });
  }, [experiences, search, category]);

  return (
    <main className="mx-auto max-w-7xl px-6 py-10">
      <div>
        <p className="text-sm font-bold uppercase tracking-wider text-indigo-600">
          Explore
        </p>

        <h1 className="mt-2 text-4xl font-black text-slate-900">
          Find your next experience
        </h1>

        <p className="mt-3 text-slate-500">
          Discover movies, events, sports and theatre experiences.
        </p>
      </div>

      <div className="mt-8 flex flex-col gap-3 md:flex-row">
        <input
          type="text"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          placeholder="Search experiences..."
          className="w-full rounded-xl border border-slate-300 bg-white px-4 py-3 outline-none focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100"
        />

        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="rounded-xl border border-slate-300 bg-white px-4 py-3 outline-none focus:border-indigo-500"
        >
          <option value="All">All Categories</option>
          <option value="Movie">Movies</option>
          <option value="Event">Events</option>
          <option value="Sports">Sports</option>
          <option value="Theatre">Theatre</option>
        </select>
      </div>

      {loading && (
        <div className="py-20 text-center">
          <div className="mx-auto h-10 w-10 animate-spin rounded-full border-4 border-slate-200 border-t-indigo-600" />
          <p className="mt-4 text-sm text-slate-500">
            Loading experiences...
          </p>
        </div>
      )}

      {!loading && error && (
        <div className="mt-10 rounded-2xl border border-red-200 bg-red-50 p-6">
          <h2 className="font-bold text-red-700">
            Could not load experiences
          </h2>

          <p className="mt-2 text-sm text-red-600">
            {error}
          </p>

          <p className="mt-3 text-xs text-red-500">
            Make sure the Spring Boot backend is running on port 8080.
          </p>
        </div>
      )}

      {!loading && !error && (
        <>
          <div className="mt-8 flex items-center justify-between">
            <p className="text-sm font-semibold text-slate-500">
              {filteredExperiences.length} experiences found
            </p>
          </div>

          <div className="mt-6 grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {filteredExperiences.map((experience) => (
              <ExperienceCard
                key={experience.id}
                experience={experience}
              />
            ))}
          </div>

          {filteredExperiences.length === 0 && (
            <div className="rounded-2xl border border-dashed border-slate-300 py-20 text-center">
              <div className="text-4xl">🔍</div>

              <h2 className="mt-4 text-xl font-bold text-slate-900">
                No experiences found
              </h2>

              <p className="mt-2 text-sm text-slate-500">
                Try another search or category.
              </p>
            </div>
          )}
        </>
      )}
    </main>
  );
}

export default Explore;